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

# Variables
SRC_DIR := src
BIN_DIR := bin
JAR_NAME := lab2.jar
MAIN_CLASS := AllocMain
CLASSPATH := .

# Create list of sources and corresponding class files
SRCS := $(shell find $(SRC_DIR) -name "*.java")
CLASSES := $(SRCS:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Targets

default: build

# Compile .java files to .class files
build0: $(BIN_DIR)
	javac -d $(BIN_DIR) -cp $(CLASSPATH) $(SRCS)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

# Create a JAR
build: build0
	jar cvfe $(JAR_NAME) $(MAIN_CLASS) -C $(BIN_DIR) .

# Clean .class files and JAR
clean:
	rm -rf $(BIN_DIR)
	rm -f $(JAR_NAME)

.PHONY: build jar clean





