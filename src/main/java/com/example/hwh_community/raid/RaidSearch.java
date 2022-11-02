package com.example.hwh_community.raid;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RaidSearch {
    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 9;

    public long getOffset() {
        return (long) (Math.max(1,page) - 1) * Math.min(size,MAX_SIZE);
    }


}
