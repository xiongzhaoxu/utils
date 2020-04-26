package com.sinry.utils;


/**
 * @Author: wangzhe
 * @Date: 2019-07-20
 */
public class FutureUtils {

//    public static <U> CompletableFuture<U> supplyAsync(BizSupplier<U> supplier, AsyncContext ttlContext) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                ttlContext.asyncStart();
//                return supplier.get();
//            } catch (BusinessException e) {
//                throw new BizRuntimeException(e.getErrorCode(), e.getMessage());
//            } finally {
//                ttlContext.asyncEnd();
//            }
//        });
//    }
//
//    @FunctionalInterface
//    public interface BizSupplier<T> {
//
//        T get() throws BusinessException;
//
//    }

}
