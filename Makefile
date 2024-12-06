ifeq ($(strip $(PROFILE)),)
	DOCKER_COMPOSE_FILE := docker-compose.yml
	ENV_FILE := .env
else
	DOCKER_COMPOSE_FILE := docker-compose.$(PROFILE).yml
	ENV_FILE := .env.$(PROFILE)
endif

ifneq ($(wildcard $(ENV_FILE)),)
	ENV_FILE_OPTION := --env-file $(ENV_FILE)
else
	ENV_FILE_OPTION :=
endif

# Target: Starts all containers defined in the Docker Compose file.
up:
	@echo "Starting $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) up -d
	@echo "$(DOCKER_COMPOSE_FILE) started successfully!"

# Target: Stops the containers without removing their volumes or images.
down:
	@echo "Stopping $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) down
	@echo "$(DOCKER_COMPOSE_FILE) stopped!"

# Target: Completely removes containers, volumes, and images.
clear:
	@echo "Removing containers, volumes, and images associated with $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) down -v --rmi all
	@echo "All containers, volumes, and images removed!"

# Target: Restarts containers without removing their volumes or images.
refresh:
	@echo "Refreshing $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) down
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) up --build --force-recreate -d
	@echo "$(DOCKER_COMPOSE_FILE) refreshed!"

# Target: Stops and removes all containers, networks, and volumes defined in the Docker Compose file.
down-v:
	@echo "Shutting down all containers, networks, and volumes defined in $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) down -v
	@echo "All containers, networks, and volumes have been removed."

# Target: Stops all containers and removes images built by the Docker Compose file.
down-i:
	@echo "Shutting down all containers and removing all images built by $(DOCKER_COMPOSE_FILE)..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) down --rmi all
	@echo "All containers have been stopped, and images removed."

# Target: Displays logs of services defined in the Docker Compose file in real-time.
logs:
	@echo "Displaying logs for services defined in $(DOCKER_COMPOSE_FILE) in real-time..."
	docker compose $(ENV_FILE_OPTION) -f $(DOCKER_COMPOSE_FILE) logs -f
