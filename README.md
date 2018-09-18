Pattern Matching Workshop

1. Parity Checks

2. Functional Programming with patterns

3. Validation

    Use pattern matching to implement the "Validator.apply" method to validate an instance of User. To be valid, the
    user should:
    
    * Have either address, phone number of email defined OR have PESEL defined
    
    * The age, if defined, should be between 0 and 100
    
    You can either encode those patterns in "extractor objects" or use case classes for pattern matching.