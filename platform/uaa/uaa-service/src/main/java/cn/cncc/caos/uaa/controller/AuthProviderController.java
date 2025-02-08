package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.service.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
public class AuthProviderController {

    /*
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }*/

//    @Value("${system.instance}")
//    private String systemInstance;

  @Autowired
  BaseUserService baseUserService;
  @Resource
  private KDHelper kdHelper;
  @Resource
  private TokenEndpoint tokenEndpoint;
  @Autowired
  @Qualifier("consumerTokenServices")
  private ConsumerTokenServices consumerTokenServices;


//  @Autowired
//  private DataAuthFeignClient dataAuthFeignClient;
  @Autowired
  private RedisTokenStore redisTokenStore;

  //下面2个方法为test
//  @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
//  public String getProduct(@PathVariable String id) {
//    //for debug
//    //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    return "product id : " + id;
//  }
//
//  @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
//  public String index1(@PathVariable String id) {
//    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    return "order id : " + id;
//  }

  /**
   * 退出登录,并清除redis中的token，该方法暂时未使用
   **/
  @RequestMapping(value = "/removeAllToken", method = RequestMethod.GET)
  public Boolean removeAllToken() {
    Collection<OAuth2AccessToken> collection = redisTokenStore.findTokensByClientId("open_client");
    for (OAuth2AccessToken oAuth2AccessToken : collection) {
      String token = oAuth2AccessToken.getValue();
      log.info(token);
      consumerTokenServices.revokeToken(token);
    }
    return true;
  }

  @RequestMapping(value = "/mylogout", method = RequestMethod.GET)
  public JwResponseData<String> removeAllToken(@RequestParam(value = "token") String token,
                                               @RequestParam(value = "realName") String realName) {
    baseUserService.addHistoryLogout(token, realName); //dataAuthFeignClient.addHistoryLogout(token,realName);
    consumerTokenServices.revokeToken(token);
    return JwResponseData.success("退出成功");
  }

//  @RequestMapping(value = "/test", method = RequestMethod.GET)
//  public String test() {
//    List<String> a = new ArrayList<>();
//    ArrayList<String> b = new ArrayList<>();
//    String result = "";
//    if (a instanceof List) {
//      result = result + "a1";
//    }
//
//    if (b instanceof List) {
//      result = result + "b1";
//    }
//
//    return result;
//  }

  @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
  public Object login(Principal principal,
                      @RequestParam Map<String, String> map
  ) {
    return baseUserService.login(principal, map, tokenEndpoint);
  }
}

