package org.luckyjourney.service.video.impl;

import org.luckyjourney.entity.video.VideoType;
import org.luckyjourney.mapper.video.VideoTypeMapper;
import org.luckyjourney.service.video.VideoTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class VideoTypeServiceImpl extends ServiceImpl<VideoTypeMapper, VideoType> implements VideoTypeService {

}
