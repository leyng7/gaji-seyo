package com.gajiseyo.modules.member.service;

import com.gajiseyo.infra.oauth2.CustomOAuth2UserService;
import com.gajiseyo.modules.file.domain.ProgFile;
import com.gajiseyo.modules.file.service.FileService;
import com.gajiseyo.modules.member.auth.MemberOauthAdapter;
import com.gajiseyo.modules.member.domain.Member;
import com.gajiseyo.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional
    public void updateNickname(Member currentUser, String nickname) {
        Member member = memberRepository.findById(currentUser.getId()).orElseThrow();
        member.changeNickname(nickname);

        Member principalMember = getPrincipalMember();
        principalMember.changeNickname(nickname);

        /*
        MemberOauthAdapter memberOauthAdapter = new MemberOauthAdapter(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                principal.getAttributes(),
                principal.getName(),
                member
        );

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                memberOauthAdapter,
                authentication.getAuthorities(),
                authentication.getAuthorizedClientRegistrationId()
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        */
    }


    @Transactional
    public void updateProfile(Member currentUser, MultipartFile picture, String nickname) {
        Member member = memberRepository.findById(currentUser.getId()).orElseThrow();
        member.changeNickname(nickname);

        Member principalMember = getPrincipalMember();
        principalMember.changeNickname(nickname);

        if (picture != null) {
            ProgFile memberPicture = member.getPicture();
            ProgFile uploadFile = fileService.uploadFile(ObjectUtils.isEmpty(memberPicture) ? null : memberPicture.getId(), "member", picture, member);

            ProgFile findPicture = fileService.getFileByIdAndSeq(uploadFile.getId(), uploadFile.getSeq());
            member.changePicture(findPicture);

            principalMember.changePicture(findPicture);
        }
    }


    public Member getPrincipalMember() {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return ((MemberOauthAdapter) authentication.getPrincipal()).getMember();
    }
}
