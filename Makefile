#src/
#|-- common/
#|   |-- IntermediateRepresentation/
#|       |-- IntermediateList.java
#|       |-- IntermediateNode.java
#|   |-- ASCIIConstants.java
#|   |-- Token.java
#|-- Main.java
#|-- Parser.java
#|-- Scanner.java

# Defining variables
JAVAC = javac
JAVA = java

# Finding all the Java files in the current directory and its subdirectories
SOURCE_FILES = $(shell find . -name "*.java")
CLASS_FILES = $(SOURCE_FILES:.java=.class)

# Targets
all: build

build: compile

compile: $(CLASS_FILES)

%.class: %.java
	$(JAVAC) $<

run: build
	$(JAVA) -cp src Main $(ARGS)

clean:
	find . -name "*.class" -exec rm {} +



