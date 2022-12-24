# 1.1.5 -> 1.2 版本更新日志

==Java==
<hr>

* Revised algorithm
* Add a layer of API interface
* Fix the priority error of residue operation
* Performance optimization. Unnecessary objects are wrapped in parentheses so that they can be recycled as soon as
  possible
* Performance optimization, finalizing more objects so that they can be better inlined
* Add interval summation component
* Optimize shared pool algorithm
* Add interval accumulation component
* Provide comparison operation support for result objects
* Optimize the logic of the check expression to improve the fault tolerance of the algorithm.

<hr>

* 修正算法
* 新增一层API接口
* 修复取余运算优先级错误
* 性能优化，为不必要的对象进行了括号包裹，使其尽快被回收
* 性能优化，为更多对象进行了终态化 使其可以被更好的内联
* 新增区间求和组件
* 优化共享池算法
* 新增区间累乘组件
* 为结果对象提供比较运算支持
* 优化检查表达式的逻辑，提高算法容错率。

<hr>