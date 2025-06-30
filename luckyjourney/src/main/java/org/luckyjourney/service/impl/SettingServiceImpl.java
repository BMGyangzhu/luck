package org.luckyjourney.service.impl;

import org.luckyjourney.entity.Setting;
import org.luckyjourney.mapper.SettingMapper;
import org.luckyjourney.service.SettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

}
