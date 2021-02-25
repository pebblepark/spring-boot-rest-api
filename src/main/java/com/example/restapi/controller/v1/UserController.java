package com.example.restapi.controller.v1;

import com.example.restapi.advice.exception.CUserNotFoundException;
import com.example.restapi.entity.User;
import com.example.restapi.model.response.CommonResult;
import com.example.restapi.model.response.ListResult;
import com.example.restapi.model.response.SingleResult;
import com.example.restapi.repo.UserJpaRepo;
import com.example.restapi.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})    // UserController 를 대표하는 최상단 타이틀
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다") // 해당 resource 타이틀과 설명
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러 건인경우 getListResult 를 이용해서 결과 출력
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUser(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) throws Exception {
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,      // 파라미터에 대한 설명
                                   @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
                                     @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                        .msrl(msrl).uid(uid).name(name).build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다.")
    @DeleteMapping(value = "/user")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @RequestParam long msrl) {
        userJpaRepo.deleteById(msrl);

        // 성공 정보만 필요한 경우 getSuccessResult()를 이용하여 결과 출력
        return responseService.getSuccessResult();
    }


}
