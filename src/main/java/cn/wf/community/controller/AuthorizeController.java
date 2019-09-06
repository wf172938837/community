package cn.wf.community.controller;

import cn.wf.community.beans.dto.AccessTokenDTO;
import cn.wf.community.beans.dto.GithubUser;
import cn.wf.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String doLogin(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        String accessToken=gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=gitHubProvider.getUser(accessToken);
        System.out.println(githubUser.getName()+"id："+githubUser.getId());
        return "index";
    }
}
