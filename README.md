# External Sorting

Lexicographical sorting of large file lines modeling

## Idea

1. Split the input file into N files (where N depends on set memory properties in _GlobalProperties.java_);
2. Sort them with Collections.sort(lines) (QuickSort, e.g.);
3. Use MergeSort to peek one line from each file from stored maxHeap and write it to the output file.

## How to run

Firstly, build last version of executable jar by 2 variants depending on your system.

Windows-like:
```$xslt
> assembly.bat
```

*nix-like:
```$xslt
> assembly.sh
```

Then start the program using command line and arguments
```$xslt
> java -jar sorting.jar
    
   Arguments:
            --demonstrate            - generate data/input.txt, sort it and write output into data/output.txt;
            --generate OUTPUT        - generate big file data/FILE_NAME with random lines
            --sort INPUT OUTPUT      - read data/INPUT, apply External Sorting algorithm and write result to the data/OUTPUT 
```