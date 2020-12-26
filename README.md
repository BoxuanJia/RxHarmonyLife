# 此项目仿照[rxjava-RxLife](https://github.com/liujingxing/rxjava-RxLife)编写，目的是方便在Harmony上，可以配合生命周期自动销毁Rxjava的管道。
关于LICENSE我不太懂，如果有问题，请及时联系我，我会及时修改。

暂时只支持rxjava2
### RxHarmonyLife
绑定Ability/AbilitySlice的生命周期，在其销毁时，自动关闭RxJava管道

### Setup
Download
```groovy
implementation 'com.github.boxuanjia:rxharmonylife:1.0.0'
```

And use
```java
Observable.timer(5, TimeUnit.SECONDS)
    .as(RxLife.as(this))     //此时的this Ability/AbilitySlice对象
    .subscribe(aLong -> {
        HiLog.info(new HiLogLabel(HiLog.LOG_APP, 0x0, "RxLife"), "accept =" + aLong);
    });
```

# Licenses
```
Copyright 2020 boxuanjia

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

