import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.sass']
})
export class FilterComponent {

  options = [{ name: 'Mentions', value: 'MENTIONS', selected: false }, { name: 'From', value: 'FROM', selected: false }];

  @Output() applyFilter = new EventEmitter<any>();

  constructor() { }

  onSelectOption(options, option, event) {
    option.selected = event.checked;
  }

  onApplyFilter() {
    this.applyFilter.emit(this.options)
  }

}
