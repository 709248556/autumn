package com.autumn.util.automap;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.autumn.util.AutoMapUtils;
import com.autumn.util.automap.model.RoleInput;
import com.autumn.util.automap.model.UserInfo;
import com.autumn.util.automap.model.UserInput;
import com.autumn.util.json.JsonUtils;

/**
 * @author 老码农
 * <p>
 * 2017-11-15 16:06:11
 */
public class AutoMapTest {

    @Test
    public void inputMapTest() {

        UserInput input = new UserInput();
        input.setUserId(1);
        input.setUserName("administartor");

        List<UserInput> inputs = new ArrayList<>();
        inputs.add(input);
        inputs.add(input);
        inputs.add(input);

        RoleInput role = new RoleInput();
        role.setRoleName("user");
        input.getRoles().add(role);

        role = new RoleInput();
        role.setRoleName("adnub");
        input.getRoles().add(role);

        UserInfo userInfo = AutoMapUtils.map(input, UserInfo.class);
        System.out.println(JsonUtils.toJSONString(userInfo));


        List<UserInput> userInfos = AutoMapUtils.mapForList(inputs, UserInput.class);
        System.out.println(JsonUtils.toJSONString(userInfos));


    }

}
