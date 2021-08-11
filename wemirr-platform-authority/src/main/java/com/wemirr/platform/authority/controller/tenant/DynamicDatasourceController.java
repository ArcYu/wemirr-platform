package com.wemirr.platform.authority.controller.tenant;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wemirr.framework.boot.entity.PageRequest;
import com.wemirr.framework.boot.utils.BeanUtilPlus;
import com.wemirr.framework.commons.entity.Result;
import com.wemirr.framework.database.mybatis.conditions.Wraps;
import com.wemirr.platform.authority.domain.dto.DynamicDatasourceReq;
import com.wemirr.platform.authority.domain.entity.tenant.DynamicDatasource;
import com.wemirr.platform.authority.service.DynamicDatasourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Levin
 */
@Slf4j
@RestController
@RequestMapping("/databases")
@RequiredArgsConstructor
@Tag(name = "数据源管理", description = "数据源管理")
@Validated
public class DynamicDatasourceController {

    private final DynamicDatasourceService dynamicDatasourceService;

    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping
    public Result<Page<DynamicDatasource>> page(PageRequest pageRequest, String database) {
        final Page<DynamicDatasource> page = dynamicDatasourceService.page(pageRequest.buildPage(),
                Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getDatabase, database));
        return Result.success(page);
    }

    @Operation(summary = "查询可用", description = "查询可用数据源")
    @GetMapping("/active")
    public Result<List<DynamicDatasource>> queryActive() {
        return Result.success(this.dynamicDatasourceService.list(Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getLocked, false)));
    }

    @Operation(summary = "Ping数据库")
    @GetMapping("/{id}/ping")
    public Result<ResponseEntity<Void>> ping(@PathVariable Long id) {
        this.dynamicDatasourceService.ping(id);
        return Result.success();
    }

    @Operation(summary = "添加数据源")
    @PostMapping
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtil.toBean(req, DynamicDatasource.class));
        return Result.success();
    }

    @Operation(summary = "编辑数据源")
    @PutMapping("/{id}")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtilPlus.toBean(id, req, DynamicDatasource.class));
        return Result.success();
    }

    @Operation(summary = "删除数据源")
    @DeleteMapping("/{id}")
    public Result<ResponseEntity<Void>> remove(@PathVariable Long id) {
        dynamicDatasourceService.removeDatabaseById(id);
        return Result.success();
    }
}
