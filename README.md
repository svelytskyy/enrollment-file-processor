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

## Solution:

### 2. Parentheses Validation
- See classes `LispChecker` and `LispCheckerTest`.

### 3. CSV File Processing
- If input CSV files are constantly coming to FTP or S3, it will require some streaming implementation and a File Watcher which listens for new files and pushes them into a queue like Kafka/SQS with the file location to be processed. `FileReaders` can be implemented as `ThreadExecutorService` with a thread pool. The Producer-Consumer design pattern can also be implemented, with the Producer as the `ReaderService` and the Consumer as the `WriterService`. The Observer pattern can also be implemented.


