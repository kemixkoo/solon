/*
 * Copyright 2017-2024 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.annotation;

import org.noear.solon.core.handle.Handler;
import java.lang.annotation.*;


/**
 * 后置处理（针对 Controller 或 Action ，附加后置处理器）
 *
 * <pre><code>
 * @Before({StartHandler.class, IpHandler.class})
 * @After(EndHandler.class)
 * @Controller
 * public class DemoController{
 *     @Mapping("/demo/*")
 *     public String hello(){
 *         return "heollo world;";
 *     }
 * }
 * </code></pre>
 *
 * @author noear
 * @since 1.0
 * @deprecated 2.9
 * */
@Deprecated
@Inherited //要可继承
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface After {
    Class<? extends Handler>[] value();
}
