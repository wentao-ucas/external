package cn.cncc.caos.uaa.aspect;

import cn.cncc.caos.common.core.aspect.BaseTimeCostAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeCostAspect extends BaseTimeCostAspect {
  @Override
  public boolean isLogSwitch() {
    return true;
  }

  @Override
  public String[] getIgnoreURIs() {
    return null;
  }

  @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
  private void countTime() {
  }

  @Around("countTime()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    return super.around(point);
  }
}
