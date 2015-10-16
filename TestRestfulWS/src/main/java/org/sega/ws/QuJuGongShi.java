package org.sega.ws;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * Created by glp on 2015/10/15.
 */

@RestController
public class QuJuGongShi {

    @RequestMapping(value= "/qjgs", method = RequestMethod.POST)
    @ResponseBody
    public String execute(@RequestBody String input_str){
        return input_str;
    }
}
