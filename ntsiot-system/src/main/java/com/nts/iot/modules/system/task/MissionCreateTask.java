package com.nts.iot.modules.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.system.model.RealTask;
import com.nts.iot.modules.system.model.TaskInstance;
import com.nts.iot.modules.system.service.RealTaskService;
import com.nts.iot.modules.system.service.TaskInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.nts.iot.constant.ConstantClass.*;

@Component
////@JobHandler
@EnableScheduling
public class MissionCreateTask {

    @Autowired
    private TaskInstanceService taskInstanceService;

    @Autowired
    private RealTaskService realTaskService;

    // 每1分钟执行一次
     @Scheduled(cron = "0  */1  *  *  *  ?")
    public void execute() {
        /* 1.任务创建相关task  */
        // 当前日期
        LocalDate localDate = LocalDate.now();
        /* 查询出  ②已经启动 ③开始时间小于当前时间 的任务实例列表 */
        List<TaskInstance> taskTemps = taskInstanceService.getTimeInList();
        if (taskTemps!=null && taskTemps.size() >0){
            for (TaskInstance taskInstance : taskTemps) {
                // 重复条件：如果按照每周来的，则判断今天周几
                if (!taskInstance.getRepeatTime().equals(TASK_REPEAT_SINGLE) && !taskInstance.getRepeatTime().equals(TASK_REPEAT_DAY)){
                    List<String> weekList = Arrays.asList(taskInstance.getRepeatTime().split(","));
                    Calendar a = Calendar.getInstance();
                    // java 中每周从周日开始，所以需要减一，因为串了一位，所以0 就是周日就是 7
                    int weekDay = a.get(Calendar.DAY_OF_WEEK);
                    weekDay = weekDay - 1;
                    if(weekDay == 0){
                        weekDay = 7;
                    }
                    // 如果创建的周中未包含设置的周
                    if (!weekList.contains("week" + weekDay)){
                        continue ;
                    }
                }
                /* check 日期 */
                if (taskInstance.getActiveDateStart()!=null && taskInstance.getActiveDateEnd()!=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date startDate = sdf.parse(taskInstance.getActiveDateStart() + " 00:00:00");
                        Date endDate  = sdf.parse(taskInstance.getActiveDateEnd() + " 23:59:59");
                        long now = System.currentTimeMillis();
                        // 如果当前时间小于开始日期或者大于结束日期了
                        if (now <= startDate.getTime() || now >= endDate.getTime()){
                            continue ;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue ;
                    }
                }
                QueryWrapper<RealTask> realTaskWrapper = new QueryWrapper<>();
                realTaskWrapper.eq("task_temp_id", taskInstance.getId());
                realTaskWrapper.eq("yyyymmdd", localDate.toString());
                List<RealTask> realTasks = realTaskService.list(realTaskWrapper);
                // 创建任务
                if (realTasks == null || realTasks.size() == 0) {
                    realTaskService.create(taskInstance, localDate.toString());
                    if (TASK_REPEAT_SINGLE.equals(taskInstance.getRepeatTime())){
                        // 创建任务之后更改任务状态为未激活
                        taskInstanceService.updateTaskStatus(taskInstance.getId(), "1");
                    }
                }
            }
        }
    }
}
