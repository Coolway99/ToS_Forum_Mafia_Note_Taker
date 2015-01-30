build:
	rm -Rf bin
	mkdir -p bin
	javac -d bin -sourcepath src src/main/Main.java
run:
	java -cp bin main.Main "$(file)"
build-app:
	mkdir -p lib
	wget https://java.net/projects/appbundler/downloads/download/appbundler-1.0.jar
	mv appbundler-1.0.jar lib
	touch Info.plist
	mkdir -p dist
	ant bundle-FMNT
	rm Info.plist
	rm -R lib
