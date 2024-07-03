.PHONY: clean build image

clean:
	docker run --rm -v ${CURDIR}:/workspace -w /workspace alpine rm -Rf build

build:
	mkdir -p build
	docker run --rm  --user $(id -u):$(id -g)\
	                 -v ${CURDIR}:/workspace\
					 -w /workspace\
					 gradle:8.8-jdk17\
					 sh -c "gradle bootJar"


