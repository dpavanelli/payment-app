.PHONY: clean build image

clean:
	rm -rf build

build:
	mkdir -p build
	docker run --rm --user $(id -u):$(id -g)\
                  -v ${CURDIR}:/workspace \
                  -w /workspace \
                  node:22.4-alpine3.19 \
                  sh -c "npm install && npm run build"

