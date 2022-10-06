package com.gajiseyo;

import com.gajiseyo.modules.item.domain.Category;
import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.domain.Status;
import com.gajiseyo.modules.item.repository.ItemRepository;
import com.gajiseyo.modules.member.domain.Member;
import com.gajiseyo.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        Member member = new Member("naver001");
        member.changeNickname("천운령");
        memberRepository.save(member);

        Item item = Item.create(
            "가지 팝니다.",
            Category.PROCESSED_FOOD,
            5000,
            true,
            false,
            "신선하고 맛있는 가지 사세요 ~",
            0,
            Status.SALE,
            false
        );

        itemRepository.save(item);
    }

}
