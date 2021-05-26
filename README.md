
#
#                       WEB SERVER
#
    Create an http server using raw java socket. 
    The http server would have 2 two or more endpoints which are,
    1. '/' path: This endpoint should handle GET requests. 
    Requests to this path should return a HTML content parsable by the browser
    
    2. '/json' path: This endpoint should handle GET requests to the '/json' path 
    and should return a JSON object recognized by the browser as a JSON object.

    The html page and json object returned should be read from the file system.
#
    How will I complete this project?
    3. Organise the folders for your module (application), 
    to house both your code base and the tests.
    4. Write tests to cover all the methods to be written, before development begins (TDD).
    5. Make logical assumptions where necessary.
#
    Steps to evaluate.
    6. Code
    a. At Minimum
    i. The right visibility modifiers should be used.
    ii. The following concepts/constructs should be used as much as possible
    · Clean code
    · Use thread to handle multiple request
    · Response with correct headers eg.
    · Content-type
    · Status code etc
    7. Test Coverage
    b. At Minimum
    iii. The tests should cover the methods as well as 
    the conditions/procedures that the methods employed
