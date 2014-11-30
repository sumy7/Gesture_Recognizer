Gesture Recognizer
=======
a gesture recognizer in Java  

-----------------
This is reimplement version from ReDollar(<https://github.com/finscn/ReDollar>).  

Thanks a lot to [fattyboy](http://fattyboy.cn), and his Online [Demo](http://fattyboy.cn/gt/)  

-----------------
## Simple Example
```java
    OneDollar oneDollar = new OneDollar();
    oneDollar.add(new Gesture("example").add(new Point(1,1)).add(new Point(2,2)));
    
    int matchindex = oneDollar.match(new Gesture("usergesture").add(new Point(0,0)).add(new Point(1,1)));
    // match() will return the matched gesture in oneDollar, if no match return -1
```

----------------

## Test GUI
There is a simple GUI application. You could use it to input or test the gesture data in visual mode.  
----------------

## Built-in unistrokes repository
The data is from <http://depts.washington.edu/aimgroup/proj/dollar/dollar.js>.  
You can refer to this picture to learn more.  
![unistrokes](https://raw.githubusercontent.com/sumy7/Gesture_Recognizer/master/src/test/unistrokes.gif)  

----------------
## Feedback
Any idea is welcome.  
If you find any mistake include `README.md` , please tell me. ≥﹏≤  
