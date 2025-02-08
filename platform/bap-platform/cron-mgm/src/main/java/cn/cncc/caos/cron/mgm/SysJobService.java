package cn.cncc.caos.cron.mgm;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysJobService {
  @Autowired
  private Scheduler scheduler;
}
