/**
 * 特殊工具类包，该包的工具类依赖于Spring容器，需要在spring容器初始化完成后使用
 * <note>
 * 会从spring容器中获取类，放入工具类的holder中
 * </note>
 *
 * @author far2away
 * @see com.github.far2away.core.util.holder.SpringUtils
 * @see com.github.far2away.core.spring.context.SpringContextHolder
 * @since 2021/10/15
 */
@NonNullApi
@NonNullFields
package com.github.far2away.core.util.holder;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;