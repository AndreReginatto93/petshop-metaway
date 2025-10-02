import { DatePipe } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

export interface TableColumn {
  field: string;  // nome do campo no objeto
  header: string; // título da coluna
  type?: 'date' | 'string' | 'number' | 'datetime';
}

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss'],
  providers: [DatePipe]
})
export class DataTableComponent {
  @Input() columns: TableColumn[] = [];
  @Input() data: any[] = [];
  @Input() loading: boolean = false;
  @Input() canEdit: boolean = true;
  @Input() canDelete: boolean = true;

  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();

  constructor(private datePipe: DatePipe) {}

  format(value: any, type?: string) {
    if (type === 'date' && value) {
      return this.datePipe.transform(value, 'dd/MM/yyyy');
    }
    if (type === 'datetime' && value) {
      return this.datePipe.transform(value, 'dd/MM/yyyy HH:mm');
    }
    return value;
  }

  getNestedValue(obj: any, path: string) {
    return path.split('.').reduce((acc, part) => acc && acc[part], obj);
  }
}
