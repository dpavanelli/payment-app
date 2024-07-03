.PHONY: clean build image

clean:
	docker run --rm -v ${CURDIR}:/backend -w /backend alpine rm -Rf build

build:
	mkdir -p build
	docker run --rm  --user $(id -u):$(id -g)\
	                 -v ${CURDIR}:/backend\
					 -w /backend\
					 gradle:8.8-jdk17\
					 sh -c "gradle bootJar"


