package org.luckyjourney.service.user.impl;

import org.luckyjourney.entity.user.FavoritesVideo;
import org.luckyjourney.mapper.user.FavoritesVideoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luckyjourney.service.user.FavoritesVideoService;
import org.springframework.stereotype.Service;


@Service
public class FavoritesVideoServiceImpl extends ServiceImpl<FavoritesVideoMapper, FavoritesVideo> implements FavoritesVideoService {

}
