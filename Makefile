export DOCKER_VERSION=minikube
export PROJECT_NAME=qrcheck
export APPLICATION_NAME=google-auth
export ANSIBLE_PATH_CONFIGS=.deploy/ansible/configs

gradle-build:
	@echo "Gradle Build"
	@./gradlew build

docker-build:
	@echo "Switch context to Minikube"
	@kubectl config use-context minikube

	@echo "Docker Build"
	@eval $$(minikube docker-env) && docker build -t ${PROJECT_NAME}/${APPLICATION_NAME}:${DOCKER_VERSION} .

ansible:
	@echo "Generating configs and Starting Service"
	@ansible-playbook -i .deploy/ansible/inventories/local --vault-password-file=.deploy/ansible/ansible_vault_local.txt -e env=local -e minikube=yes  .deploy/ansible/main.yaml

start: gradle-build docker-build ansible

stop:
	@echo "Removing and Stopping Service"
	@kubectl delete all -l app=${APPLICATION_NAME}
	@kubectl delete cm -l app=${APPLICATION_NAME}
	@kubectl delete secret -l app=${APPLICATION_NAME}
	@eval $$(minikube docker-env) && docker rmi ${PROJECT_NAME}/${APPLICATION_NAME}:${DOCKER_VERSION}

restart: stop start

ansible-generate-config-local:
	@echo "Deleting the previous configs. Path: $(ANSIBLE_PATH_CONFIGS)"
	@rm -rf ${ANSIBLE_PATH_CONFIGS}

	@echo "Generating local configs to a path ${ANSIBLE_PATH_CONFIGS}"
	@ansible-playbook -i .deploy/ansible/inventories/local --vault-password-file=.deploy/ansible/ansible_vault_local.txt -e env=local -e minikube=no .deploy/ansible/main.yaml

ansible-generate-config-test:
	@echo "Deleting the previous configs. Path: $(ANSIBLE_PATH_CONFIGS)"
	@rm -rf ${ANSIBLE_PATH_CONFIGS}

	@echo "Generating local configs to a path ${ANSIBLE_PATH_CONFIGS}"
	@ansible-playbook -i .deploy/ansible/inventories/test --vault-password-file=.deploy/ansible/ansible_vault_test.txt -e env=test -e minikube=no .deploy/ansible/main.yaml