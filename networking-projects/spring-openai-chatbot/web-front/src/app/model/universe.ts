export interface Universe {
  id: string;
  label: string;
}

export interface SubUniverse {
  universeId: string;
  subUniverses: Universe[];
}
