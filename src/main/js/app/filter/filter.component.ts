import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.sass']
})
export class FilterComponent {

  @Output() applyFilter = new EventEmitter<any>();

  @Input() options: any;

  selection = [];

  constructor() { }

  onSelectOption(event: MatSelectChange) {
    this.selection.push(event.value);
  }

  onApplyFilter() {
    this.applyFilter.emit(this.selection)
  }

}
