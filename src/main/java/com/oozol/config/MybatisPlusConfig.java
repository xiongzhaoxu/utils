//package com.oozol.config;
//
//import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * mybatis-plus配置
// *
// */
//@Configuration
//public class MybatisPlusConfig {
//
//    /**
//     * 分页插件
//     *
//     * @return PaginationInterceptor
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//
//    /**
//     * 乐观锁
//     * https://baomidou.gitee.io/mybatis-plus-doc/#/optimistic-locker-plugin
//     *
//     * @return OptimisticLockerInterceptor
//     */
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
//    }
//
//}