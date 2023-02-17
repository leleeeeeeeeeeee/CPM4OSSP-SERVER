package io.jpom.permission;

import cn.hutool.core.comparator.PropertyComparator;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.jiangzeyin.common.spring.SpringUtil;
import cn.jiangzeyin.controller.base.AbstractController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jpom.common.BaseServerController;
import io.jpom.model.BaseModel;
import io.jpom.model.data.RoleModel;
import io.jpom.model.data.UserModel;
import io.jpom.plugin.ClassFeature;
import io.jpom.service.user.RoleService;

import java.util.*;
import java.util.stream.Collectors;

/**
 *

 */
public interface BaseDynamicService {
}
