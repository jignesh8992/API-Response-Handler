# API-Response-Handler
Handle exceptions thrown in retrofit with kotlin coroutines

## Usage
[MainActivity.kt](https://github.com/jignesh8992/API-Response-Handler/blob/master/app/src/main/java/com/example/responsehandler/MainActivity.kt)
```kotlin
// Make sure the Internet is working before making any API call
if (!isOnline()) {
    showNoInternetAlert(object : OnPositive {
        override fun onYes() {
            // re-call api
        }
    })
    return
}

// Internet is working so now you can make API call
launch {
    
    try {               

        //  Make request and wait until get response
        val reqAPI = apiClient.<your-method>
        val resAPI = reqAPI.await()

        // Validating API response
        if (isValidResponse(resAPI)) {                 
            // Success: Perform your further task
           
        } else {
            // Generic exception occurred
            errorAlert(object : OnPositive {
                override fun onYes() {
                    // re-call api
                }
            })
       
        }
        
    } catch (exception: Exception) {
        // Exception occurred
        val errorMessage = apiExceptionHandler(exception, object : OnPositive {
            override fun onYes() {
                // re-call api
            }
        })
    }
}
```


## Contributing
Feel free to contribute code to Battery-Information. You can do it by forking the repository via Github and sending pull request with changes.

When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible. Also be sure that all tests are passing.
 
## Developed By
[Jignesh N Patel](https://github.com/jignesh8992) - [jignesh8992@gmail.com](https://mail.google.com/mail/u/0/?view=cm&fs=1&to=jignesh8992@gmail.com&su=https://github.com/jignesh8992/Battery-Information&body=&bcc=jignesh8992@gmail.com&tf=1)

  <a href="https://github.com/jignesh8992" rel="nofollow">
  <img alt="Follow me on Google+" 
       height="50" width="50" 
       src="https://github.com/jignesh8992/Battery-Information/blob/master/social/github.png" 
       style="max-width:100%;">
  </a>
  
  <a href="https://www.linkedin.com/in/jignesh8992/" rel="nofollow">
  <img alt="Follow me on LinkedIn" 
       height="50" width="50" 
       src="https://github.com/jignesh8992/Battery-Information/blob/master/social/linkedin.png" 
       style="max-width:100%;">
  </a>
  
  <a href="https://twitter.com/jignesh8992" rel="nofollow">
  <img alt="Follow me on Facebook" 
       height="50" width="50"
       src="https://github.com/jignesh8992/Battery-Information/blob/master/social/twitter.png" 
       style="max-width:100%;">
  </a>
  
  <a href="https://www.facebook.com/jignesh8992" rel="nofollow">
  <img alt="Follow me on Facebook" 
       height="50" width="50" 
       src="https://github.com/jignesh8992/Battery-Information/blob/master/social/facebook.png" 
       style="max-width:100%;">
  </a>
