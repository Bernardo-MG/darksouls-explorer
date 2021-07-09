import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { NamedValue } from 'app/models/namedValue';

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.sass']
})
export class FilterComponent {

  @Output() applyFilter = new EventEmitter<any>();

  @Input() options: NamedValue[];

  selections = [];

  constructor() { }

  onSelectOption(event: MatSelectChange, index: number) {
    this.selections[index].value = event.value;
  }

  onAddSelection() {
    this.selections.push({ value: "" });
  }

  onRemoveSelection(index: number) {
    this.selections.splice(index, 1);
  }

  onApplyFilter() {
    const values = this.selections.map((s) => s.value);
    this.applyFilter.emit(values)
  }

}
