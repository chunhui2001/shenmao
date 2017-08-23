import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
  name: 'date',
})
export class DateFormatPipe implements PipeTransform {
  transform(value: string, format: string) {
    const datePipe = new DatePipe('en-US');
    let f = 'dd/MM/yyyy';
    if (format === 'f1') { f = 'yyyy年MM月dd日'; }
    value = datePipe.transform(value, f);
    return value;
  }
}

