/**
 * 字典值项服务依赖于字典类型服务，
 * 字典类型服务不依赖于字典值项服务，
 * 通过触发事件，避免双向依赖。
 * <p>
 * //TODO Spring 是否支持响应式的事件发布体系？事件中事务是否支持？
 * <p>
 * Reactor 体系中，查询不到记录不会触发异常。
 *
 * @author peace
 **/
package com.github.peacetrue.dictionary.modules;
