package com.qmlx.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qmlx.usercenter.common.BaseResponse;
import com.qmlx.usercenter.common.ErrorCode;
import com.qmlx.usercenter.common.ResultUtils;
import com.qmlx.usercenter.exception.BusinessException;
import com.qmlx.usercenter.model.domain.Team;
import com.qmlx.usercenter.model.domain.User;
import com.qmlx.usercenter.model.dto.TeamQuery;
import com.qmlx.usercenter.model.request.TeamJoinRequest;
import com.qmlx.usercenter.model.vo.TeamUserVO;
import com.qmlx.usercenter.service.TeamService;
import com.qmlx.usercenter.service.UserService;
import com.qmlx.usercenter.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
//@CrossOrigin(origins ={"http://localhost:3000"})
@RequestMapping ("/team")
@Api(value = "队伍相关接口")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "添加队伍",notes = "需要传入一个队伍类，返回插入成功之后的队伍id")
    public BaseResponse<Long> addTeam(@RequestBody Team team, HttpServletRequest request){
        if(team==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User currentUser = UserUtils.getCurrentUser(request);
        if(currentUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long id=currentUser.getId();
        long teamId = teamService.addTeam(team, currentUser);
        return ResultUtils.success(teamId);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除队伍",notes = "需要传入一个队伍id，删除成功返回true")
    public BaseResponse<Boolean> deleteTeam(@RequestBody long id,HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User currentUser = UserUtils.getCurrentUser(request);
        if(currentUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        boolean isDelete=teamService.delteTeam(id,currentUser);
        return ResultUtils.success(isDelete);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新队伍",notes = "需要传入一个队伍类，必须包含id，修改成功之后返回true")
    public BaseResponse<Boolean> updateTeam(@RequestBody Team team){
        if(team==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean save = teamService.updateById(team);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "查询队伍",notes = "需要传入一个队伍id，根据id查询一个队伍")
    public BaseResponse<Team> getTeamById(long id){
        if(id<=0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = teamService.getById(id);
        if(team==null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"查询失败");
        }
        return ResultUtils.success(team);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询队伍列表",notes = "按照封装类参数查询队伍列表")
    public BaseResponse<List<TeamUserVO>> getTeamList(TeamQuery teamQuery,HttpServletRequest request){
        if(teamQuery==null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数不能为空");
        }
        Team team = new Team();
        User loginUser = UserUtils.getCurrentUser(request);
        List<TeamUserVO> teamUserResult=teamService.listTeams(teamQuery,loginUser);
        //使用对下个拷贝

        return ResultUtils.success(teamUserResult);
    }

    @PostMapping("/list/page")
    @ApiOperation(value = "队伍分页查询",notes = "按照封装类参数查询队伍列表")
    public BaseResponse<Page<Team>> listTeamByPage(@RequestBody TeamQuery teamQuery){
        if(teamQuery==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = new Team();
        //使用对下个拷贝
        try {
            BeanUtils.copyProperties(team,teamQuery);
        } catch (BeansException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
        Page<Team> page=new Page<>(teamQuery.getPageNum(),teamQuery.getPageSize());
        Page<Team> teamPage = teamService.page(page);
        return ResultUtils.success(teamPage);
    }

    @GetMapping("/list/my/create")
    @ApiOperation(value = "我创建的队伍",notes = "查询我创建的队伍")
    public BaseResponse<List<Team>> listmyCreateTeam( TeamQuery teamQuery,HttpServletRequest request){
        //获取当前登录用户
        User currentLoginUser = UserUtils.getCurrentUser(request);
        if(currentLoginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        List<Team> myTeamList=teamService.listMyTeam(teamQuery,currentLoginUser);

       return ResultUtils.success(myTeamList);
    }

    @GetMapping("/list/my/join")
    @ApiOperation(value = "我加入的队伍",notes = "查询我加入的队伍")
    public BaseResponse<List<Team>> listmyJoinTeam( TeamQuery teamQuery,HttpServletRequest request){
        //获取当前登录用户
        User currentLoginUser = UserUtils.getCurrentUser(request);
        if(currentLoginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        List<Team> myTeamList=teamService.listMyJoinTeam(teamQuery,currentLoginUser);

        return ResultUtils.success(myTeamList);
    }

    @PostMapping("/join")
    @ApiOperation(value = "加入队伍",notes = "传入加入的队伍id")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest,HttpServletRequest request){
        if (teamJoinRequest==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User loginUser = UserUtils.getCurrentUser(request);
        if(loginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Boolean isJoin=teamService.joinTeam(teamJoinRequest,loginUser);
        return ResultUtils.success(isJoin);
    }











}
