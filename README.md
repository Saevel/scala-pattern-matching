Pattern Matching Workshop

1. Parity Checks

2. Functional Programming with patterns

3. Validation

    Use pattern matching to implement the "Validator.apply" method to validate an instance of User. To be valid, the
    user should:
    
    * Have either address, phone number of email defined OR have PESEL defined
    
    * The age, if defined, should be between 0 and 100
    
    You can either encode those patterns in "extractor objects" or use case classes for pattern matching.
    
4. Process control & exception handling

    Use pattern matching to implement the PostProcessor.apply method. It takes Status, an equivalent of 
    Tuple2[Try[Unit], Try[Int]] where the first Try refers to the attempt to parse program configuration and the second
    one refers to the attempt to execute the program and return an Int. 
    
    The PostProcessor.apply method should:
    
    * if parsing failed, return Left with an Error containing the message from the causing Throwable and an indicator
    of the fact that it should not be retried
    
    * if parsing suceeded, but execution failed with a RuntimeException, return Left with an Error containing the
     message from the causing Throwable and an indicator of the fact that it should be retried
     
    * if parsing suceeded, but execution failed with a non-RuntimeException, return Left with an Error containing the
         message from the causing Throwable and an indicator of the fact that it should not be retried  