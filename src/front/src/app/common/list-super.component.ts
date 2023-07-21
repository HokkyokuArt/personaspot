import { Component, Inject, OnInit } from "@angular/core";
import { ISuperModel } from "./super.model";
import { SuperService } from "./super.service";
import { IPageable } from "./pageable.model";
import { LayoutService } from "../components/layout/layout.service";
import { Router } from "@angular/router";

interface IAction {
    label: string;
    property: string;
}

@Component({
    template: ''
})
export abstract class ListSuperComponent<T extends ISuperModel, S extends SuperService<T>> implements OnInit {

    list?: IPageable<T>;
    one?: T;

    config!: Array<IAction>;

    constructor(@Inject(SuperService) public service: S, protected layoutService: LayoutService, protected router: Router) { }

    ngOnInit(): void {
        this.getList(undefined, undefined, undefined, this.gerSortOrderList());
    }

    get contentList(): Array<T> {
        return this.list?.content ?? [];
    }

    abstract gerSortOrderList(): string;

    public getList(page: number = 0, pageSize: number = 15, sortDirection: 'ASC' | 'DESC' = 'ASC', field: string = 'uuid'): void {
        this.service.list(page, pageSize, sortDirection, field).subscribe({
            next: v => {
                this.list = v;
            },
            error: er => {
                this.layoutService.error('Erro');
                console.log('ERRO', er);
            }
        });
    }

    public getOne(uuid: string): void {
        this.service.getOne(uuid).subscribe({
            next: v => {
                this.one = v;
            },
            error: er => {
                console.log('ERRO', er);
            }
        });
    }

    public onSave(model: T): void {
        this.service.save(model).subscribe({
            next: v => {
                this.one = v;
            },
            error: er => {
                console.log('ERRO', er);
            }
        });
    }

    public onUpdate(uuid: string, model: T): void {
        this.service.update(uuid, model).subscribe({
            next: v => {
                this.one = v;
            },
            error: er => {
                console.log('ERRO', er);
            }
        });
    }

    public onDelete(uuid: string): void {
        this.service.delete(uuid).subscribe({
            next: () => {
                const toExclude = this.list?.content.find(s => s.uuid === uuid);

                if (this.list && toExclude) {
                    // const index = this.list.content.indexOf(toExclude);
                    // const inicio = this.list.content.slice();
                    // inicio.splice(index, 1);
                    // this.list.content = inicio;

                    this.list.content = this.list.content.filter(item => item !== toExclude);

                }
            },
            error: er => {
                this.layoutService.error('Erro');
            }
        });
    }
}
