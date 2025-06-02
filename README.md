# AI Assist

AI Assist is a command-line tool that leverages OpenAI's GPT models to help users with terminal operations through natural language interactions.

## Features

The application provides two main commands:

1. **ai-help**: An enhanced version of the traditional `man` command, providing comprehensive help for any command with usage examples.
   
2. **ai-cmd**: Generates command-line commands based on natural language descriptions.

## Requirements

- Java 21 or higher
- Maven 3.6 or higher
- OpenAI API key

## Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   cd ai_assist
   ```

2. Configure your OpenAI API key:
   - Open `src/main/resources/application.yaml`
   - Add your OpenAI API key:
     ```yaml
     spring:
       ai:
         openai:
           api-key: your-api-key-here
     ```

3. Build the application:
   ```
   ./mvnw clean package
   ```

## Usage

### Running the application

```
java -jar target/ai_assist-0.0.1-SNAPSHOT.jar [command] [options]
```

Or if you've built a native image:

```
target/ai_assist [command] [options]
```

### Available Commands

#### ai-help

Get detailed help for any command:

```
java -jar target/ai_assist-0.0.1-SNAPSHOT.jar ai-help -c <command>
```

Example:
```
java -jar target/ai_assist-0.0.1-SNAPSHOT.jar ai-help -c grep
```

This will provide comprehensive documentation for the `grep` command, including examples of usage.

#### ai-cmd

Generate a command based on a natural language description:

```
java -jar target/ai_assist-0.0.1-SNAPSHOT.jar ai-cmd -d "<description>"
```

Example:
```
java -jar target/ai_assist-0.0.1-SNAPSHOT.jar ai-cmd -d "find all text files containing the word 'error' in the current directory"
```

This will generate the appropriate command to accomplish the described task.

## Building a Native Image

This project supports GraalVM native image compilation for faster startup and reduced memory usage.

### Prerequisites

- GraalVM 22.3+ installed
- Native image compiler configured

### Building the Native Image

```
./mvnw native:compile -Pnative
```

Then run the native executable:

```
target/ai_assist [command] [options]
```

## Technical Details

- Built with Spring Boot 3.4.4
- Uses Spring AI framework with OpenAI's GPT-4o-mini model
- Command-line interface implemented with Picocli
- Written in Java 21
- Uses Lombok for reducing boilerplate code

## License

[Specify license information here]