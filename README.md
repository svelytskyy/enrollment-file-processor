# Enrollment File Processor

## Questions:
1. **Tell me about something you have read recently that you would recommend and why.** (Can be a Github Repo, Article, Blog, Book, etc.)
   
2. **Coding Exercise:** You are tasked with writing a checker that validates the parentheses of a LISP code. Write a program (in Java or JavaScript) that takes a string as input and returns true if all the parentheses in the string are properly closed and nested.
   
3. **Coding Exercise:** Availity receives enrollment files from various benefits management and enrollment solutions (e.g., HR platforms, payroll platforms). Most of these files are typically in EDI format. However, some files are in CSV format. For the files in CSV format, write a program in a language that makes sense to you to read the content of the file and separate enrollees by insurance company into their own files. Additionally, sort the contents of each file by last and first name (ascending). Lastly, if there are duplicate User IDs for the same Insurance Company, include only the record with the highest version. The following data points are included in the file:
   - User ID (string)
   - First Name (string)
   - Last Name (string)
   - Version (integer)
   - Insurance Company (string)

---

## Solutions:

### 1.
I'm currently learning AWS Machine Learning to prepare for the AWS Certified Machine Learning certification. You can find the official documentation here : https://docs.aws.amazon.com/machine-learning/

Why is it crucial and highly recommended for every developer? It's simple: AI is the future of technology.

Here's why AWS Machine Learning is important for developers:

- Competitive Advantage: Knowing machine learning techniques and using AWS services can give developers a big edge in today's tech-focused world.

- Scalability: AWS provides machine learning services that can fit any project's needs, making it easy for developers to create and use AI models efficiently.

- Driving Innovation: AI is behind many groundbreaking ideas in fields like healthcare, finance, and e-commerce. Developers who know AWS machine learning can be part of creating these ideas that shape the future.

### 2. Parentheses Validation
- See classes `LispChecker` and `LispCheckerTest`.

### 3. CSV File Processing
- The provided solution is simplified: all CSV records from different CSV files in the input folder are loaded into RAM, processed, and then written into separate files. The current solution can handle data up to 20 GB or approximately 500 million records.
- If the data exceeds 20 GB, a database or another solution will be required
- If input CSV files are constantly coming to FTP or S3, it will require some streaming implementation and a FileWatcher which listens for new files and pushes them into a queue like Kafka/SQS with the file location to be processed. `FileReaders` can be implemented as `ThreadExecutorService` with a thread pool. The Producer-Consumer design pattern can also be implemented, with the Producer as the `ReaderService` and the Consumer as the `WriterService`. The Observer pattern can also be implemented.

### How to Run the solution#3
- mvn package
- java -jar enrollment-file-processor-1.0.0.jar ~/input ~/output


