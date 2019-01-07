package cn.cescforz.foo.annotation;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 注释</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 21:26
 */
public class CustomAnnotation {


    /*
       @interface Annotation{ } 定义一个注解 @Annotation，一个注解是一个类

       @Override  重写一个方法
       @Deprecated 表示某个类的属性或方法已经过时，不想别人再用时，在属性和方法上用@Deprecated修饰
       @SuppressWarnings 用来压制程序中出来的警告，比如在没有用泛型或是方法已经过时的时候

       @Retention可以用来修饰注解，是注解的注解，称为元注解
       Retention注解有一个属性value，是RetentionPolicy类型的，Enum RetentionPolicy是一个枚举类型，
       etentionPolicy有3个值：
       CLASS  ：表示注解的信息被保留在class文件(字节码文件)中当程序编译时，但不会被虚拟机读取在运行的时候；
       SOURCE ：修饰的注解,表示注解的信息会被编译器抛弃，不会留在class文件中，注解的信息只会留在源文件中
       RUNTIME  ：修饰的注解，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时


       @Target也是用来修饰注解的元注解，它有一个属性ElementType也是枚举类型，
       ANNOTATION_TYPE ：注释类型声明
       CONSTRUCTOR ：构造函数声明
       FIELD ：字段声明（包括枚举常量）
       LOCAL_VARIABLE ：局部变量声明
       METHOD ：方法声明(Method declaration)
       PACKAGE ：包声明
       PARAMETER ：正式的参数声明
       TYPE ：类, 接口 (包括注释类型), 或 枚举 声明

       如@Target(ElementType.METHOD) 修饰的注解表示该注解只能用来修饰在方法上

       @Documented注解
       Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。


       @Data : 注解在类上, 为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法
       @Getter/@Setter : 注解在类上, 为类提供读写属性
       @ToString : 注解在类上, 为类提供 toString() 方法
       @Slf4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象
       @Log4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象
       @NonNull : 注解在参数上, 如果该类参数为 null , 就会报出异常,  throw new NullPointException(参数名)
       @Cleanup : 注释在引用变量前, 自动回收资源 默认调用 close() 方法
       @EqualsAndHashCode : 注解在类上, 为类提供 equals() 和 hashCode() 方法
       @NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor : 注解在类上, 为类提供无参,有指定必须参数, 全参构造函数
       @Builder : 注解在类上, 为类提供一个内部的 Builder
       @Synchronized : 注解在方法上, 为方法提供同步锁

       @Bean是一个方法级别上的注解，主要用在@Configuration注解的类里，也可以用在@Component注解的类里。添加的bean的id为方法名
       @Scope注解来指定使用@Bean定义的bean   @scope("singleton")单例/@scope("prototype")多例

       @Mapper
        作用：在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
        添加位置：接口类上面
       @MapperScan
        作用：指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类
        添加位置：是在Springboot启动类上面添加   @MapperScan({"com.kfit.demo","com.kfit.user"})

       @ServletComponentScan
        注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
       @ComponentScan
        工程中Application类的位置。默认情况下就不需要配置@ComponentScan这个注解了。
        因为Application类，在启动的时候，默认是加载和Application类所在同一个目录下的所有类，包括所有子目录下的类。所以一般情况下，启动类的位置是
        有特殊要求的。

       @PostConstruct说明
        被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
        被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。

        @PreDestroy说明
         被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
         被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前。



        @ConditionalOnClass:当前上下文存存在个类,才会创建对应的bean实例
         解释:如果写入Hello.class,那么就代表在当前的类路径下面肯定又要这样的一个类存在

        @ConditionalOnBean:当前上下文存在某个类的实例,才会创建对应的bean实例
         解释:所谓实例,就是已经被spring实例化过了,比如一般需要在类上加一些注解@Component @Service @Configuration @Respositry …..
        @ConditionalOnMissingBean:和上面整好相反,不在对应的实例,创建当前bean的实例

        @ConditionalOnMissingClass:不存在某个类,就创建当前bean的实例

        @ConditionalOnProperty:根据配置文件中的属性条件,来创建当前bean的实例
        解释:这个参数组合有好几种,下面一一列举
        1 prefix and value
        prefix:参数的前缀
        value:参数前缀后面的字段名称

        2 name and havingValue
        name:完整的字段名称
        havingValue:字段名称对应的值

        3 value and matchIfMissing
        value:配置文件里面参数的值 是否为true

        @ConditionalOnExpression:根据表达式的值,来创建当前bean的实例

        @EnableCaching注解是spring framework中的注解驱动的缓存管理功能。
        自spring版本3.1起加入了该注解。如果你使用了这个注解，那么你就不需要在XML文件中配置cache manager了。
        当你在配置类(@Configuration)上使用@EnableCaching注解时，会触发一个post processor，这会扫描每一个spring bean，
        查看是否已经存在注解对应的缓存。如果找到了，就会自动创建一个代理拦截方法调用，使用缓存的bean执行处理。

     */

    // 单行注释
    /*... 多行注释....*/
    /**.. 文本注释....*/
}
