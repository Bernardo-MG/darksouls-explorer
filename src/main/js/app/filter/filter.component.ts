import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.sass']
})
export class FilterComponent {

  @Output() applyFilter = new EventEmitter<any>();

  @Input() options: any;

  constructor() { }

  onSelectOption(options, option, event) {
    option.selected = event.checked;
  }

  onApplyFilter() {
    this.applyFilter.emit(this.options)
  }

}
