.PHONY: clean build image start stop

clean:
	$(MAKE) -C backend clean
	$(MAKE) -C frontend clean

build:
	$(MAKE) -C backend build
	$(MAKE) -C frontend build

start:
	docker-compose up --build -d

stop:
	docker-compose stop