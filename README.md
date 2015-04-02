# StyleNumberProgressBar

### 摘要

在代码家的[NumberProgressBar](https://github.com/daimajia/NumberProgressBar)基础上进行的修改；
- 实现了更多的可定制性，同时分离了类的抽象部分和它的实现部分。
- 实现组件的可重用性（实现后：抽象部分可为容器，即 Layout，也可为 View，另外实现部分也可近乎自由定制，可扩充：引入反射机制等）

![](http://7xi7e6.com1.z0.glb.clouddn.com/style_number_progressbar.png)

### 如何使用

``` xml
<com.msolo.progressbar.style.StyleNumberProgressBar
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        msolo:progress_unreached_color="#BEBEBE"
        msolo:progress_reached_color="#BE0D15"
        msolo:progress_unreached_bar_size="8dp"
        msolo:progress_reached_bar_size="8dp"
        msolo:progress_text_size="12sp"
        msolo:progress_text_color="#BE0D15"
        msolo:progress_text_offset="4dp"
        msolo:progress_text="上涨14.36%"
        msolo:progress_shape="horizontal_default"
        msolo:progress="14"
        />

    <com.msolo.progressbar.style.StyleNumberProgressBar
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        msolo:progress_unreached_color="#BEBEBE"
        msolo:progress_reached_color="#BE0D15"
        msolo:progress_unreached_bar_size="3dp"
        msolo:progress_reached_bar_size="3dp"
        msolo:progress_text_size="12sp"
        msolo:progress_text_color="#BE0D15"
        msolo:progress_text_offset="4dp"
        msolo:progress_text="2015-04-01"
        msolo:progress_timeline="2015-01-01,2016-06-01,2018-01-01"
        msolo:progress_shape="horizontal_timeline"
        msolo:progress="14"
        />
```

### License
> Copyright 2013 Pascal Welsch
>
> Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
>
>   http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

