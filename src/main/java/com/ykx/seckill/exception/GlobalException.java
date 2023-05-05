package com.ykx.seckill.exception;

import com.ykx.seckill.vo.RespBean;
import com.ykx.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    private RespBeanEnum respBean;

}
