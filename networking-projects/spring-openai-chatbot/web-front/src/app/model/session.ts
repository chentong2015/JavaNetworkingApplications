import {Universe} from "./universe";

export interface Session {
  startDate: Date;
  universe: Universe;
  paragraphs: Paragraph[];
}

export interface Paragraph {
  author: string;
  text: string;
}
