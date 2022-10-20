package com.example.hwh_community;

import org.junit.jupiter.api.Test;

public class ss {

    @Test
    void r() {

        solution(12);

    }

    int solution(int n) {
        int answer = 0;

        for(int i=1;i<n+1;i++)
        {
            if(n%i == 0)
            {
                answer += i;
            }

        }

        return answer;
    }
}
